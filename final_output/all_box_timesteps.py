import matplotlib.pyplot as plt
import numpy as np


x = [
[679.08,724.86,709.37,717.55,719.86,716.8,720.44,674.02,687.45,681.84],
[1049.95,1081.31,1036.87,1062.78,951.76,1005.47,1070.87,1061.3,1038.71,971.12],
[906.52,839.67,730.52,854.62,809.76,799.02,838.17,847.05,828.96,825.27],
]

ax1 = plt.subplot()
ax1.boxplot(x)
#ax1.set_ylim([0.45, 0.6])
plt.xlabel('approach')
plt.ylabel('average timesteps')

means = [np.mean(px) for px in x]
plt.xticks((1,2,3),('HR', 'MCTS', 'EA'))
plt.scatter([1,2,3], means)

#plt.boxplot(x)
plt.show()




