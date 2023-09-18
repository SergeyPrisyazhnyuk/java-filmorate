## Java-Filmorate
Template repository for Filmorate project.

## ER Diagramm of filmorate project
![ER Diagramm of filmorate project](/ER_diagramm/filmorate.png)

## Query Examples
### Getting Film names and genres for user id = 13

```roomsql

select u.user_name,
    f.film_name,
    g.genre_name        
 from users u 
join likes l on u.user_id = l.user_id 
join films f on f.film_id = l.film_id    
join films_genres fg on f.film_id = fg.film_id
join genres g on fg.genre_id = g.genre_id
where u.user_id = 13;

```
