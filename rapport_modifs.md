
# Modifications du fichier `Simulation.java`

## Contexte

Le projet consistait à simuler la circulation des véhicules avec des feux de signalisation. Il y avait quelques erreurs dans le code qui empêchaient le bon fonctionnement. Voici les modifications faites rapidement pour corriger le tout.

## Changements

### 1. Initialisation des listes de véhicules

**Problème :**  
Les véhicules étaient ajoutés dans les listes avant que ces dernières ne soient initialisées, ce qui posait une erreur.

**Changement :**  
J'ai initialisé les listes avant d'y ajouter les véhicules.

```java
vehiclesRight = new ArrayList<>();
vehiclesLeft = new ArrayList<>();
vehiclesDown = new ArrayList<>();
vehiclesUp = new ArrayList<>();
```

### 2. Double ajout de `v1` supprimé

**Problème :**  
Le véhicule `v1` était ajouté deux fois dans `vehiclesRight`, ce qui créait des doublons.

**Changement :**  
J'ai supprimé le deuxième ajout.

```java
// vehiclesRight.add(v1); // Supprimé
```

### 3. Ordre des initialisations

**Problème :**  
Les feux de signalisation n'étaient pas initialisés avant d'être utilisés par les véhicules.

**Changement :**  
J'ai placé l'initialisation des feux avant celle des véhicules.

```java
// Initialisation des feux avant les véhicules
trafficLights = new ArrayList<>();
TrafficLight t1 = new TrafficLight(...);
trafficLights.add(t1);
...
v1 = new Vehicle(..., trafficLights.get(1), ...);
```

### 4. Gestion des erreurs sur le chargement des images

**Problème :**  
Le `try-catch` utilisé pour le chargement des images n'était pas super explicite.

**Changement :**  
J'ai ajouté un message d'erreur simple en plus du `printStackTrace()`.

```java
try {
    car1 = ImageIO.read(getClass().getResourceAsStream("/car1.jpg"));
    mTerrain = ImageIO.read(getClass().getResourceAsStream("/road1.jpg"));
} catch (IOException e) {
    System.err.println("Erreur chargement image : " + e.getMessage());
    e.printStackTrace();
}
```

## Conclusion

J'ai fait quelques corrections rapides pour que tout fonctionne sans erreurs. Les listes sont bien initialisées, et les doublons ont été supprimés.
