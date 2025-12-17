# KanbanBoard – Backend

Backend d’une application web de gestion de projets agiles (type Kanban), développée en binôme dans un cadre académique.
J’ai réalisé l’intégralité de la partie backend, étant plus à l’aise sur cette partie.  
La partie frontend a été développée par mon binôme.

## Objectif
Fournir une API REST permettant de gérer :
- les tableaux Kanban
- les tickets (bug, feature, déplacement)
- les sections, tags, commentaires
- les utilisateurs

## Architecture
Architecture **modulaire et orientée bonnes pratiques Spring Boot** :

- `entities` : modèles métier (Board, Ticket, Section, Utilisateur, etc.)
- `dtos` : objets de transfert de données
- `repositories` : accès aux données (Spring Data JPA)
- `services` / `servicesInterface` : logique métier
- `mappers` : conversion Entity ↔ DTO
- `web` : contrôleurs REST
- `exceptions` : gestion centralisée des erreurs
- `enums` : types et statuts métier

## Technologies
- Java
- Spring Boot
- Spring Data JPA
- REST API
- MySQL
- Swagger (documentation API)

## Fonctionnalités principales
- Création et gestion des boards Kanban
- Gestion complète des tickets (création, mise à jour, déplacement)
- Ajout de commentaires et de tags
- Organisation par sections
- API REST prête à être consommée par un frontend Angular

## Auteur
Abdoulaye Djibril BARRY
