from sm_load import load_kobart_model

import logging
logging.getLogger("transformers").setLevel(logging.ERROR)

import torch
import sys # 추가

tokenizer, model = load_kobart_model('ai/summary/kobart_summary')

# === 추가 === #
if len(sys.argv) < 2:
    print("입력된 텍스트가 없습니다.")
    sys.exit(1)
# === 추가 끝 === #
# === 수정 === #
# input_text =  """
# 오늘은 정말 맑고 따뜻한 날이었다. 아침에 일어나자마자 창문을 열어보니 맑은 하늘과 따뜻한 햇살이 나를 반겨주었다. 날씨가 이렇게 좋으니 기분도 절로 좋아졌다. 요즘 바쁜 일상 속에서 잠시나마 마음의 여유를 찾을 수 있는 날이었다.
#
# 일찍 일어난 덕분에 아침 산책을 다녀오기로 마음먹었다. 공원에 도착했을 때, 공기마저 상쾌하게 느껴졌다. 도로 주변에는 봄꽃들이 만개해 있었고, 특히 벚꽃이 예쁘게 피어 있었다. 벚꽃의 하얀 꽃잎들이 바람에 날리며 땅에 떨어지는 모습은 정말 아름다웠다. 잠시 벤치에 앉아 벚꽃을 보며 마음의 평화를 느낄 수 있었다. 봄의 기운이 가득한 순간이었다.
#
# 산책을 마친 후, 오랜만에 만나는 친구와 점심을 함께 먹기로 했다. 우리는 좋아하는 음식점에 가서 맛있는 음식을 먹으며 즐거운 시간을 보냈다. 오랜만에 만나는 친구라 그런지, 우리는 서로 이야기를 나누는 동안 시간을 잊고 웃음이 끊이지 않았다. 친구의 근황과 내가 최근에 겪은 일들에 대해 이야기하며, 우리는 그동안 쌓였던 이야기들을 풀어놓을 수 있었다. 그와의 대화는 항상 즐겁고 유익하다.
#
# 점심을 마친 후에는 도서관으로 가서 공부를 했다. 오랜만에 집중해서 공부하니 머리가 맑아지는 느낌이었다. 도서관에서 책을 읽거나 필기를 하며 시간을 보냈고, 공부가 끝날 즈음에는 어느새 오후가 다 지나가 있었다. 그렇게 잠시나마 학문에 몰두할 수 있는 시간도 정말 소중하게 느껴졌다.
#
# 저녁이 되자, 가족과 함께 맛있는 식사를 했다. 오늘은 모두가 모여서 함께 음식을 나누며 대화를 나누는 시간을 가졌다. 가족들과 함께 하는 저녁은 언제나 따뜻하고 평화롭다. 서로의 일상 이야기를 들으며, 좋은 시간을 보낼 수 있었다.
#
# 오늘 하루는 정말 평화롭고 조용한 하루였다. 날씨도 좋고, 친구와의 만남, 가족과의 식사 등 작은 순간들이 모두 소중하고 행복하게 느껴졌다. 바쁜 일상 속에서도 이런 여유로운 시간을 보낼 수 있다는 것이 얼마나 큰 축복인지를 다시 한 번 깨닫게 되는 하루였다. 오늘 하루는 정말 감사하고, 행복한 시간이었다.
# """

input_text = sys.argv[1]
# === 수정 끝 === #

if len(input_text) >= 100:
    # 100자 이상일 경우 요약 수행
    text = input_text.replace('\n', ' ')

    raw_input_ids = tokenizer.encode(text)
    input_ids = [tokenizer.bos_token_id] + raw_input_ids + [tokenizer.eos_token_id]
    input_ids = torch.tensor([input_ids])

    summary_text_ids = model.generate(
        input_ids=input_ids,
        bos_token_id=model.config.bos_token_id,
        eos_token_id=model.config.eos_token_id,
        length_penalty=0.6,  # 길이에 대한 penalty 값. 1보다 작은 경우 더 짧은 문장을 생성하도록 유도
        max_length=64,  # 요약문의 최대 길이 설정
        min_length=4,  # 요약문의 최소 길이 설정
        num_beams=5,  # Beam search 탐색 폭: 성능 향상을 위해 5개의 후보를 동시에 탐색
        num_return_sequences=1,  # 하나만 출력!
        repetition_penalty=3.0,  # 반복 억제
        no_repeat_ngram_size=3,  # N-gram 반복 억제
        early_stopping=True,  # 조건 충족 시 즉시 종료
    )

    summary_text = tokenizer.decode(summary_text_ids[0], skip_special_tokens=True)

    print("="*50)
    print("📝 원문:")
    print(input_text)
    print("\n📌 요약:")
    print(summary_text)
    print("="*50)
else:
    # 100자 미만이면 원문 그대로 출력
    print("="*50)
    print("📌 요약:")
    print(input_text)
    print("="*50)