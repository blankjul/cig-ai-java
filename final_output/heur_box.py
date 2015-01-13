import matplotlib.pyplot as plt
import numpy as np

x = [
[0.53, 0.49, 0.52, 0.54, 0.48, 0.54, 0.48, 0.55, 0.5, 0.54],
[0.54, 0.53, 0.5, 0.51, 0.51, 0.51, 0.55, 0.53, 0.56, 0.55],
[0.55, 0.52, 0.51, 0.53, 0.48, 0.55, 0.52, 0.52, 0.56, 0.58],
[0.51, 0.51, 0.46, 0.5, 0.46, 0.52, 0.49, 0.49, 0.54, 0.5],
[0.53, 0.51, 0.57, 0.52, 0.52, 0.55, 0.51, 0.51, 0.52, 0.53],
[0.51, 0.51, 0.49, 0.53, 0.5, 0.54, 0.52, 0.52, 0.53, 0.56]
]


ax1 = plt.subplot()
ax1.boxplot(x)
ax1.set_ylim([0.45, 0.6])
plt.xlabel('Heuristic parameters')
plt.ylabel('average wins')

means = [np.mean(px) for px in x]
plt.scatter([1, 2, 3,4,5,6], means)

#plt.boxplot(x)
plt.show()




