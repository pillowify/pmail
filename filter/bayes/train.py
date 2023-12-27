import pandas as pd
import joblib
import time
from ast import literal_eval
from sklearn.model_selection import RepeatedKFold
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.metrics import f1_score

N_SPLITS = 10
N_REPEATS = 10
TF_IDF = True


def test(X_train, X_test, y_train, y_test, tf_idf=False):
    corpus_train = [' '.join(literal_eval(sample)) for sample in X_train]
    corpus_test = [' '.join(literal_eval(sample)) for sample in X_test]

    vectorizer = TfidfVectorizer() if tf_idf else CountVectorizer()

    corpus_train = vectorizer.fit_transform(corpus_train)
    corpus_test = vectorizer.transform(corpus_test)

    mnb = MultinomialNB()
    mnb.fit(corpus_train, y_train)

    pred = mnb.predict(corpus_test)

    accuracy = accuracy_score(list(y_test), pred)
    recall = recall_score(list(y_test), pred)
    f1 = f1_score(list(y_test), pred)

    return accuracy, recall, f1


def cross_validation(n_splits, n_repeats, tf_idf=False):
    rkf = RepeatedKFold(n_splits=n_splits, n_repeats=n_repeats)

    accuracy_avg = 0
    recall_avg = 0
    f1_avg = 0

    temp_acc = 0
    temp_recall = 0
    temp_f1 = 0

    i = 0
    for train_index, test_index in rkf.split(X, y):
        i += 1

        X_train, y_train = X[train_index], y[train_index]
        X_test, y_test = X[test_index], y[test_index]

        accuracy, recall, f1 = test(X_train, X_test, y_train, y_test, tf_idf)

        accuracy_avg += accuracy
        recall_avg += recall
        f1_avg += f1

        temp_acc += accuracy
        temp_recall += recall
        temp_f1 += f1
        
        if i % n_repeats == 0:
            print('acc:\t{0}\nrecall:\t{1}\nf1:\t{2}\n'.format(temp_acc / n_splits, temp_recall / n_splits, temp_f1 / n_splits))
            temp_acc = 0
            temp_recall = 0
            temp_f1 = 0
        

    accuracy_avg /= n_splits * n_repeats
    recall_avg /= n_splits * n_repeats
    f1_avg /= n_splits * n_repeats

    return accuracy_avg, recall_avg, f1_avg


def save_model(X, y, tf_idf=False):
    corpus = [' '.join(literal_eval(sample)) for sample in X]

    vectorizer = TfidfVectorizer() if tf_idf else CountVectorizer()

    corpus = vectorizer.fit_transform(corpus)

    mnb = MultinomialNB()
    mnb.fit(corpus, y)

    joblib.dump(vectorizer, "./model/vectorizer.joblib")
    joblib.dump(mnb, "./model/mnb.joblib")


if __name__ == '__main__':
    data = pd.read_csv("./data/data.csv")
    X, y = data["text"], data["spam"]

    seed = int(time.time())

    # accuracy, recall, f1 = cross_validation(N_SPLITS, N_REPEATS, TF_IDF)

    # print('acc:\t{0}\nrecall:\t{1}\nf1:\t{2}\n'.format(accuracy, recall, f1))

    save_model(X, y, TF_IDF)
