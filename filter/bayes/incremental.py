import os
import email
import jieba
import re
import pandas as pd
import base64
from nltk.corpus import stopwords


STAGE_HAM_DATASET_PATH = './dataset/raw/stage/ham'
STAGE_SPAM_DATASET_PATH = './dataset/raw/stage/spam'

def get_body(raw_text):
    mail = email.message_from_string(raw_text)
    enc = mail['Content-Transfer-Encoding']
    if mail.is_multipart():
        for part in mail.walk():
            c_type = part.get_content_type()
            if c_type == 'text/plain':
                body = part.get_payload()
                break
    else:
        body = mail.get_payload()

    if enc == 'base64':
        body = base64.b64decode(body).decode('utf-8')
    
    return body


def process(body):
    stop_words = set(stopwords.words("english") + stopwords.words("chinese"))
    text = re.sub("[^\u4e00-\u9fa5^a-z^A-Z]+", ' ', body).lower()
    text_list = jieba.lcut(text)
    text_list = [
        word for word in text_list if word not in stop_words and len(word) > 1]
    return text_list


ham_files = os.listdir(STAGE_HAM_DATASET_PATH)
spam_files = os.listdir(STAGE_SPAM_DATASET_PATH)

corpus = []
labels = []

for file in ham_files:
    path = STAGE_HAM_DATASET_PATH + '/' + file
    with open(path, 'r') as raw_file:
        raw_text = raw_file.read()
        body = get_body(raw_text)
        corpus.append(process(body))
        labels.append(0)
    os.remove(path)

for file in spam_files:
    path = STAGE_SPAM_DATASET_PATH + '/' + file
    with open(path, 'r') as raw_file:
        raw_text = raw_file.read()
        body = get_body(raw_text)
        corpus.append(process(body))
        labels.append(1)
    os.remove(path)

data = pd.DataFrame({"text": corpus, "spam": labels})
old_data = pd.read_csv("./data/data.csv")[["text", "spam"]]

new_data = pd.concat([old_data, data], ignore_index=True)
new_data.to_csv("./data/data.csv")
