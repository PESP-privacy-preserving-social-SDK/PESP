twitter_original = [188,169,167,162,162,174,166,158,165,164]
twitter_delta = [60, 67, 51, 111,50, 41, 50, 103, 43, 50]
twitter_modified = [x + y for x, y in zip(twitter_original, twitter_delta)]
# print(len(twitter_original))
# print(len(twitter_delta))
# print(twitter_modified)


import numpy as np
import scipy.stats as st
avg = np.mean(twitter_original)
(lo, hi) = st.t.interval(confidence=0.95, df=len(twitter_original)-1, loc=np.mean(twitter_original), scale=st.sem(twitter_original))
print(avg, hi - avg)

avg = np.mean(twitter_modified)
(lo, hi) = st.t.interval(confidence=0.95, df=len(twitter_modified)-1, loc=np.mean(twitter_modified), scale=st.sem(twitter_modified))
print(avg, hi - avg)