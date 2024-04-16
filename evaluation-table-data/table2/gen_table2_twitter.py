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
            if count % 3 == 0:
                logs.append(temp)
                temp = list()

    def getTime(line):
        ret = line[:23]

        dateTime = parser.parse(ret)
        return dateTime

    time_data = list()

    l = []
    for three_line in logs:
        three_line = [getTime(l) for l in three_line]
        # network_time = int(four_line[1].split("response time:")[1])
        # print("network time: ", network_time)


        delta1 = three_line[2] - three_line[0]

   
        delta2 = delta1.microseconds / 1000


        l.append(delta2)
        
        
    
    return l

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

    l = []

    for two_line in logs:
        two_time = [getTime(l) for l in two_line]
        two_pkgname = [getPkgname(l) for l in two_line]

        delta1 = two_time[1] - two_time[0]
        l.append(delta1.microseconds / 1000)
    
    return l


baselinel = getTimeList("baseline_twlogin_log.txt")
pespl = getPESPTimeList("pesp_twlogin_log.txt")

def computeInterval(input_list):
    avg = np.mean(input_list)
    (lo, hi) = st.t.interval(confidence=0.95, df=len(input_list)-1, loc=np.mean(input_list), scale=st.sem(input_list))
    print(avg, hi - avg, lo, hi)


computeInterval(baselinel)

computeInterval([a + b for (a, b) in zip(baselinel, pespl)])
