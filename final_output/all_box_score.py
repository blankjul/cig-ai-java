import matplotlib.pyplot as plt
import numpy as np


x = [
[312.15,94.12,77.72,190.25,165.49,199.54,270.33,138.75,152.81,135.79],
[293.2,273.98,250.32,123.32,231.44,131.48,302.42,169.59,272.7,180.77],
[217.07,177.35,213.93,166.46,194.76,103.81,145.47,189.09,194.26,85.97],
]



ax1 = plt.subplot()
ax1.boxplot(x)
#ax1.set_ylim([0.45, 0.6])
plt.xlabel('approach')
plt.ylabel('average score')

means = [np.mean(px) for px in x]
plt.xticks((1,2,3),('HR', 'MCTS', 'EA'))
plt.scatter([1,2,3], means)

#plt.boxplot(x)
plt.show()




