# MEMOIRE TRANSACTIONNELLE

ce projet est une implementation d'une mémoire transactionnelle avec verroux en écriture 
vous trouverez avec ce code un PDF qui explique le principe d'une mémoire transactionnelle ainsi que 2 exemples d'usage.

## Count to one Million
- cet exemple consiste à faire une incrementation de la case d'index 0 sur la mémoire globale jusqu'à 1M, en utilisant 100 threads chacun va exécuter 10000 transactions afin de faire une incrementation par chaque transaction
- Pour exécuter cet exemple veuillez utiliser le script **countTo1m.sh**

## UP DOWN ARRAY
- cet exemple va créer un tableau de taille 1024 qui suit le pattern suivant : [1, 2, 3, ...510, 511, 512, 512, 511, 510,.... 3, 2, 1]
- 512 thread chacun va incrementer **les cases I..1024-I** avec I le ID du thread
- Pour exécuter cet exemple veuillez utiliser le script **UpDownArray.sh**
