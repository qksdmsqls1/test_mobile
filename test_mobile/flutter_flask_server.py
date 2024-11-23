from flask import Flask, request, jsonify
import torch
import pymysql
from PIL import Image
import io
from flask_cors import CORS
import sys
from pathlib import Path
import pathlib

# pathlib 경로 문제 해결 코드
temp = pathlib.PosixPath
pathlib.PosixPath = pathlib.WindowsPath

app = Flask(__name__)
CORS(app)  # CORS 설정 추가 (모든 도메인에서의 요청 허용)

# DB 연결 정보
db_settings = {
    'host': 'codingmaker.net',
    'port': 33060,
    'user': 'khs0624',
    'password': '0624',
    'database': 'khs0624',
    'charset': 'utf8mb4',
}

# YOLOv5 경로 설정
yolov5_path = Path(r'C:\Users\gmltjr\yolov5')
if yolov5_path.exists():
    sys.path.append(str(yolov5_path))
else:
    raise FileNotFoundError(f"YOLOv5 directory not found at {yolov5_path}. Please check the path.")

# YOLOv5 모델 로드
try:
    model = torch.hub.load(str(yolov5_path), 'custom', path=str(yolov5_path / 'runs/train/exp22/weights/best.pt'), source='local', device='cpu')
except FileNotFoundError as fnf_error:
    raise FileNotFoundError(f"Model file not found at specified path: {fnf_error}")
except Exception as e:
    raise Exception(f"Failed to load YOLOv5 model due to an unexpected error: {str(e)}")

# /predict 엔드포인트 정의
@app.route('/predict', methods=['POST'])
def predict():
    print("POST /predict 요청 도착")
    # 업로드된 이미지 파일 가져오기
    image = request.files.get('image')
    if image is None:
        return jsonify({'error': 'No image provided'}), 400

    # 이미지를 PIL 형식으로 변환
    try:
        image = Image.open(io.BytesIO(image.read()))
    except Exception as e:
        return jsonify({'error': f'Invalid image format: {str(e)}'}), 400

    # YOLOv5 모델을 사용해 이미지 예측 수행
    try:
        results = model(image)
        if len(results.xyxy[0]) == 0:
            return jsonify({'error': 'No object detected'}), 400

        # YOLOv5에서 첫 번째 객체의 클래스 번호 추출
        detected_class = int(results.xyxy[0][0][-1].item())

        # DB에서 drug_num에 해당하는 데이터 조회
        connection = pymysql.connect(**db_settings)
        try:
            with connection.cursor() as cursor:
                query = "SELECT * FROM drug_info WHERE drug_num = %s"
                cursor.execute(query, (detected_class,))
                result = cursor.fetchone()

                if result is None:
                    return jsonify({'error': 'No data found for detected class'}), 404

                # DB에서 조회한 데이터 반환
                response = {
                    'drug_num': result[0],
                    'drug_name': result[1],
                    'formulation': result[2],
                    'color': result[3],
                    'Separating_Line': result[4],
                    'image': result[5],
                    'efficacy': result[6],
                    'usage_method': result[7],
                    'warning': result[8],
                    'precautions': result[9],
                    'interactions': result[10],
                    'side_effects': result[11],
                    'storage_method': result[12]
                }
                return jsonify(response)
        finally:
            if 'connection' in locals() and connection is not None:
                connection.close()

    except Exception as e:
        return jsonify({'error': str(e)}), 500
    
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
