from sa_load import load_model 
import torch
import torch.nn.functional as F
import sys # 추가

# 모델, 토크나이저, 디바이스, 라벨 불러오기
model, tokenizer, device, emotion_labels = load_model()

# === 추가 === #
if len(sys.argv) < 2:
    print("입력된 텍스트가 없습니다.")
    sys.exit(1)
# === 추가 끝 === #

# 4. 분석할 텍스트
# === 수정 === #
# diary_text = '''오늘 정말 놀라운 일이 있었어요. 오전에 길을 걷고 있었는데, 갑자기 무슨 소리가 들리더라구요? 그래서 고개를 돌려봤는데, 그게 뭐였는지 알아요? 무려 길거리에서 고양이들이 춤을 추고 있는 거예요!! 진짜 눈을 의심했어요. 처음에 "어, 이건 뭐지?" 하고 그냥 지나칠 뻔 했는데, 고양이들이 진짜 리듬을 타며 뛰어다니면서 춤을 추는 거였어요. 그 모습이 너무 웃겨서 혼자서 큰 소리로 웃었어요. (아니, 진짜 이게 무슨 일이죠?)
#
# 그래서 순간 너무 놀라서 핸드폰을 꺼내서 영상 찍으려고 했는데, 그때 고양이 한 마리가 나를 쳐다보면서 갑자기 춤을 멈췄어요. 그리고 나한테 "이게 뭐야, 이 사람 뭐야?" 하는 표정을 지었어요. 그 표정을 보고 진짜 너무 당황스러웠고, "내가 미쳤나?" 싶은 기분이 들었어요.
#
# 결국, 고양이들은 다시 춤을 추기 시작했고, 나는 그냥 그 장면을 놓칠까 봐 계속 보고만 있었어요. 나중에 보니 정말 미쳤나 싶은 일을 길거리에서 보고 있었던 거죠. 그 장면은 진짜 잊을 수가 없어요. 저, 다시는 그런 일이 없을 거라 믿었는데... 하하, 너무 놀라운 하루였어요.'''
diary_text = sys.argv[1]
# === 수정 끝 === #

# 5. 토큰화 및 텐서 변환
def preprocess(text):
    encoded = tokenizer(text, 
                        return_tensors='pt', 
                        truncation=True, 
                        padding='max_length', 
                        max_length=512)
    return encoded['input_ids'], encoded['attention_mask'], encoded['token_type_ids']

input_ids, attention_mask, token_type_ids = preprocess(diary_text)
input_ids, attention_mask, token_type_ids = input_ids.to(device), attention_mask.to(device), token_type_ids.to(device)

# 6. 예측
with torch.no_grad():
    logits = model(input_ids, attention_mask, token_type_ids)
    probs = F.softmax(logits, dim=1)
    predicted = torch.argmax(probs, dim=1).item()

# 7. 감정 라벨 매핑 (예시)
predicted_emotion = emotion_labels[predicted]


def analyze_diary_all(diary_text):
    inputs = tokenizer(diary_text, return_tensors="pt", truncation=True, padding=True, max_length=512)
    inputs = {key: value.to(device) for key, value in inputs.items()}

    with torch.no_grad():
        outputs = model(**inputs)

    logits = outputs
    probabilities = torch.sigmoid(logits)
    emotion_scores = probabilities.cpu().numpy().flatten()
    result = {emotion_labels[i]: emotion_scores[i] for i in range(len(emotion_scores))}
    return result

# 분석 실행
analysis_result = analyze_diary_all(diary_text)

model = model.to('cpu')

# 결과 출력
print("감정 분석 결과:")
print(f"예측된 감정: {predicted_emotion}")
for emotion, score in analysis_result.items():
    print(f"{emotion}: {score*100:.1f}%")