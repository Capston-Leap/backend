model_path = "ai/emotion/kobert_emotion_model_2.pt"
import torch
from transformers import AutoTokenizer, AutoModel
from torch import nn
import torch.nn.functional as F

emotion_labels = ["불안", "분노", "상처", "슬픔", "당황", "기쁨", "놀람"]

# 1. Tokenizer & Pretrained KoBERT 로드
tokenizer = AutoTokenizer.from_pretrained("monologg/kobert", trust_remote_code=True)
kobert_model = AutoModel.from_pretrained("monologg/kobert")

# 2. 감정 분류기 모델 정의 (예시: 7개 감정 분류 기준)
class KoBERTClassifier(nn.Module):
    def __init__(self, bert, hidden_size=768, num_classes=7, dr_rate=0.5):
        super(KoBERTClassifier, self).__init__()
        self.bert = bert
        self.dropout = nn.Dropout(p=dr_rate)
        self.classifier = nn.Linear(hidden_size, num_classes)

    def forward(self, input_ids, attention_mask, token_type_ids):
        _, pooled_output = self.bert(input_ids=input_ids, 
                                     attention_mask=attention_mask, 
                                     token_type_ids=token_type_ids, 
                                     return_dict=False)
        out = self.dropout(pooled_output)
        return self.classifier(out)

# 3. 모델 인스턴스 생성 및 로드
def load_model(model_path="ai/emotion/kobert_emotion_model_2.pt"):
    device = torch.device("cpu")
    model = KoBERTClassifier(kobert_model)
    model.load_state_dict(torch.load(model_path, map_location=device))
    model.to(device)
    model.eval()
    return model, tokenizer, device, emotion_labels

if __name__ == "__main__":
    # 테스트용 실행 방지
    pass