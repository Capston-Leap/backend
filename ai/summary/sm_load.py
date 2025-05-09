from transformers import PreTrainedTokenizerFast, BartForConditionalGeneration

def load_kobart_model(model_binary_path='ai/summary/kobart_summary'):
    tokenizer = PreTrainedTokenizerFast.from_pretrained('gogamza/kobart-base-v1')
    model = BartForConditionalGeneration.from_pretrained(model_binary_path)
    return tokenizer, model