def getscord(i):
    temp = -1
    while temp<0 or temp>100:
        temp = float(input("请输入第"+str(i+1)+"个学生的成绩"))
    return temp

scord = [getscord(i) for i in range(10)]

print('最高：'+str(max(scord)))
print('最底：'+str(min(scord)))
print('及格：'+str(sum([1 for i in scord if i > 60])))