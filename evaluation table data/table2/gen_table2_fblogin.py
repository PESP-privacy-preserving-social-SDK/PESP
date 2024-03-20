from time import time
from dateutil import parser
from datetime import timedelta
import numpy as np
import scipy.stats as st


def getMigrated():
    logs = list()
    count = 0
    with open("login_migrated_log.txt", "r") as fp:
        temp = list()
        for line in fp.readlines():
                # print(line)
            temp.append(line)
            count += 1
            if count % 4 == 0:
                logs.append(temp)
                temp = list()

    def getTime(line):
        line = line[:23]
        # print(line)
        dateTime = parser.parse(line)
        return dateTime

    time_data = list()

    for four_line in logs:
        four_time = [getTime(l) for l in four_line]
        # print(four_time)
        delta1 = four_time[1] - four_time[0]
        # print(delta1.microseconds / 1000)
        # print("")     
        delta2 = four_time[3] - four_time[2]
        # print(delta2.microseconds / 1000)
        # print("")
        delta3 = delta1 + delta2
        # print("")
        # print(delta3.microseconds / 1000)
        time_data.append(delta3.microseconds / 1000)

        # delta4 = four_time[3] - four_time[0]
        # print(delta4)
        # print(delta4.seconds * 1000)
        # print(delta4.microseconds)
        # print(delta4.seconds * 1000 + delta4.microseconds / 1000)
        # time_data.append(delta4.seconds * 1000 + delta4.microseconds / 1000)
    avg = np.mean(time_data)
    (lo, hi) = st.t.interval(confidence=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data))
    print(avg, hi - avg, lo, hi)

def getOriginal():
    logs = list()
    count = 0
    with open("login_original_log.txt", "r") as fp:
        temp = list()
        for line in fp.readlines():
                # print(line)
            temp.append(line)
            count += 1
            if count % 4 == 0:
                logs.append(temp)
                temp = list()

    def getTime(line):
        line = line[:23]
        # print(line)
        dateTime = parser.parse(line)
        return dateTime

    time_data = list()

    for four_line in logs:
        four_time = [getTime(l) for l in four_line]
        # print(four_time)
        delta1 = four_time[1] - four_time[0]
        # print(delta1.microseconds / 1000)
        # print("")     
        delta2 = four_time[3] - four_time[2]
        # print(delta2.microseconds / 1000)
        # print("")
        delta3 = delta1 + delta2
        # print("")
        # print(delta3.microseconds / 1000)
        time_data.append(delta3.microseconds / 1000)

        # delta4 = four_time[3] - four_time[0]
        # print(delta4)
        # print(delta4.seconds * 1000)
        # print(delta4.microseconds)
        # print(delta4.seconds * 1000 + delta4.microseconds / 1000)
        # time_data.append(delta4.seconds * 1000 + delta4.microseconds / 1000)


        

    avg = np.mean(time_data)
    (lo, hi) = st.t.interval(confidence=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data))
    print(avg, hi - avg, lo, hi)

getOriginal()

getMigrated()


# display profile migrated
# time_data = [51, 51, 46, 41, 41, 39, 52, 44, 36, 40, 56, 38, 37, 41, 41, 37, 29, 46] 
# time_data = [x + 50 for x in time_data]
# print(st.t.interval(alpha=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data)) )


# display profile original
# time_data = [0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1] 
# time_data = [x + 50 for x in time_data]
# print(len(time_data))
# print(st.t.interval(alpha=0.95, df=len(time_data)-1, loc=np.mean(time_data), scale=st.sem(time_data)) )

