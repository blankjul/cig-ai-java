import matplotlib.pyplot as plt
import numpy as np

x = [
[0.55,0.52,0.51,0.53,0.48,0.55,0.52,0.52,0.56,0.58],
[0.44,0.44,0.43,0.35,0.47,0.41,0.43,0.38,0.45,0.42],
[0.46,0.51,0.51,0.47,0.48,0.47,0.44,0.48,0.47,0.5],
]

ax1 = plt.subplot()
ax1.boxplot(x)
#ax1.set_ylim([0.45, 0.6])
plt.xlabel('approach')
plt.ylabel('average wins')

means = [np.mean(px) for px in x]
plt.xticks((1,2,3),('HR', 'MCTS', 'EA'))
plt.scatter([1,2,3], means)

#plt.boxplot(x)
plt.show()




