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
            if count % 2 == 0:
                logs.append(temp)
                temp = list()

    def getTime(line):
        ret = line[:23]

        dateTime = parser.parse(ret)
        return dateTime

    first_l = []
    second_l = []

    for two_line in logs:
        two_time = [getTime(l) for l in two_line]

        delta1 = two_time[1] - two_time[0]


        
        if all(["first" in pkgname for pkgname in two_line]):
            first_l.append(delta1.microseconds / 1000)
        elif all(["second" in pkgname for pkgname in two_line]):
            second_l.append(delta1.microseconds / 1000)
        else:
            print("ERROR in log input, pgkname in a group of 2 please")
    
    return first_l, second_l

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

        return ret

    def getTime(line):
        ret = line[:23]

        dateTime = parser.parse(ret)
        return dateTime

    first_l = []
    second_l = []
    for two_line in logs:
        two_time = [getTime(l) for l in two_line]
        two_pkgname = [getPkgname(l) for l in two_line]

        delta1 = two_time[1] - two_time[0]
 
        
        if all(["first" in pkgname for pkgname in two_pkgname]):
            first_l.append(delta1.microseconds / 1000)
        elif all(["second" in pkgname for pkgname in two_pkgname]):
            second_l.append(delta1.microseconds / 1000)
        else:
            print("ERROR in log input, pgkname in a group of 2 please")
    
    return first_l, second_l


baseline_first_l, baseline_second_l = getTimeList("baseline_fbprofile_log.txt")
pesp_first_l, pesp_second_l = getPESPTimeList("pesp_fbprofile_log.txt")

def computeInterval(input_list):
    avg = np.mean(input_list)
    (lo, hi) = st.t.interval(confidence=0.95, df=len(input_list)-1, loc=np.mean(input_list), scale=st.sem(input_list))
    print(avg, hi - avg, lo, hi)


computeInterval(baseline_first_l)
computeInterval(baseline_second_l)
computeInterval([a + b for (a, b) in zip(baseline_first_l, pesp_first_l)])
computeInterval([a + b for (a, b) in zip(baseline_second_l, pesp_second_l)])
