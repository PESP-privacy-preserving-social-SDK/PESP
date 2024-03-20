from time import time
from dateutil import parser
from datetime import timedelta
import numpy as np
import scipy.stats as st



# display profile original
time_data = [0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1] 
time_data = [x + 50 for x in time_data]

avg = np.mean(time_data)
(lo, hi) = st.t.interval(confidence=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data))
print(avg, hi - avg, lo, hi)
# (avg, hi) = st.t.interval(confidence=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data))
# print(avg, hi - avg)

# display profile migrated
time_data = [51, 51, 46, 41, 41, 39, 52, 44, 36, 40, 56, 38, 37, 41, 41, 37, 29, 46] 
time_data = [x + 50 for x in time_data]
avg = np.mean(time_data)
(lo, hi) = st.t.interval(confidence=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data))
print(avg, hi - avg, lo, hi)

# (avg, hi) = st.t.interval(confidence=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data))
# print(avg, hi - avg)