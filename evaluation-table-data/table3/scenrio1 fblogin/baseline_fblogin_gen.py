from time import time
from dateutil import parser
from datetime import timedelta
import numpy as np
import scipy.stats as st


def getTimeList(fname):

    logs = list()
    count = 0
    with open(fname, "r") as fp:
        temp = list()
        for line in fp.readlines():
            temp.append(line)
            count += 1
            if count % 4 == 0:
                logs.append(temp)
                temp = list()

    # def getPkgname(line):
    #     ret = line[36:70]
    #     print(ret)
    #     return ret

    def getTime(line):
        ret = line[:23]
        # print(ret)
        dateTime = parser.parse(ret)
        return dateTime

    time_data = list()

    first_l = []
    second_l = []
    # print(len(logs))
    for four_line in logs:
        four_time = [getTime(l) for l in four_line]
        # four_pkgname = [getPkgname(l) for l in four_line]
        # print(four_pkgname)
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

        
        if all(["first" in pkgname for pkgname in four_line]):
            first_l.append(delta3.microseconds / 1000)
        elif all(["second" in pkgname for pkgname in four_line]):
            second_l.append(delta3.microseconds / 1000)
        else:
            print("ERROR in log input, pgkname in a group of 4 please")
    
    return first_l, second_l


    # delta4 = four_time[3] - four_time[0]
    # print(delta4)
    # print(delta4.seconds * 1000)
    # print(delta4.microseconds)
    # print(delta4.seconds * 1000 + delta4.microseconds / 1000)
    # time_data.append(delta4.seconds * 1000 + delta4.microseconds / 1000)

def getPESPTimeList(fname):

    logs = list()
    count = 0
    with open(fname, "r") as fp:
        temp = list()
        for line in fp.readlines():
            temp.append(line)
            count += 1
            if count % 2 == 0:
                logs.append(temp)
                temp = list()

    def getPkgname(line):
        ret = line[36:70]
        # print(ret)
        return ret

    def getTime(line):
        ret = line[:23]
        # print(ret)
        dateTime = parser.parse(ret)
        return dateTime

    first_l = []
    second_l = []
    # print(len(logs))
    for two_line in logs:
        two_time = [getTime(l) for l in two_line]
        two_pkgname = [getPkgname(l) for l in two_line]
        # print(two_line)
        # print(two_pkgname)
        delta1 = two_time[1] - two_time[0]
        # print(delta1.microseconds / 1000)

        
        if all(["first" in pkgname for pkgname in two_pkgname]):
            first_l.append(delta1.microseconds / 1000)
        elif all(["second" in pkgname for pkgname in two_pkgname]):
            second_l.append(delta1.microseconds / 1000)
        else:
            print("ERROR in log input, pgkname in a group of 2 please")
    
    return first_l, second_l


baseline_first_l, baseline_second_l = getTimeList("baseline_fblogin_log.txt")
pesp_first_l, pesp_second_l = getPESPTimeList("pesp_fblogin_log.txt")

def computeInterval(input_list):
    # print(input_list)
    avg = np.mean(input_list)
    (lo, hi) = st.t.interval(confidence=0.95, df=len(input_list)-1, loc=np.mean(input_list), scale=st.sem(input_list))
    print(avg, hi - avg, lo, hi)


computeInterval(baseline_first_l)
computeInterval(baseline_second_l)
computeInterval([a + b for (a, b) in zip(baseline_first_l, pesp_first_l)])
computeInterval([a + b for (a, b) in zip(baseline_second_l, pesp_second_l)])
