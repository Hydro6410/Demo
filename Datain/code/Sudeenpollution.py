import os
import pandas
import numpy as np
import matplotlib.pyplot as plt

def getQ(lines):
    Q = []
    for i in range(1, len(lines)):
        tempQ = lines[i].split('\t')[0]
        Q += [float(tempQ)]
    return np.array(Q)

path = '../Suddenpollution/'
files = os.listdir(path)
Q = []
for file in files:
    fin = open(path+file)
    lines = fin.readlines()
    fin.close()
    tempQ = getQ(lines)
    Q += [tempQ]
    fig, ax = plt.subplots()
    x = np.linspace(0,1, len(Q[0]))
    ax.plot(x, tempQ, 'k--')
    plt.savefig(str(file.split('.')[0])+'jpg')


