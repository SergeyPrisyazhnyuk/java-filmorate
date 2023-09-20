## Java-Filmorate
Template repository for Filmorate project.

## ER Diagramm of filmorate project
![ER Diagramm of filmorate project](/ER_diagramm/filmorate.png)



## ER Diagramm code
https://dbdiagram.io/d 

```
// Use DBML to define your database structure
// Docs: https://dbml.dbdiagram.io/docs

Table users {
  user_id integer [primary key]
  user_name varchar
  email varchar
  login varchar
  birthady date
}

Table films {
  film_id integer [primary key]
  film_name varchar
  description varchar
  release_date timestamp
  duration integer
  rating_id integer
}

Table likes {
  likes_id integer [primary key]
  film_id integer [ref: > films.film_id]
  user_id integer [ref: > users.user_id]
}

Table friends {
  friends_id integer [primary key]
  user_id integer
  friend_id integer
  status boolean
}

Table rating {
  rating_id integer [primary key]
  rating_name varchar
}

Table films_genres {
  films_genres_id integer [primary key]
  film_id integer 
  genre_id integer
}

Table genres {
  genre_id integer [primary key]
  genre_name varchar
}

//Ref: likes.user_id > users.user_id // many-to-one
//Ref: likes.film_id > films.film_id // many-to-one
Ref: users.user_id < friends.user_id // one-to-many
Ref: users.user_id < friends.friend_id // one-to-many
Ref: films.rating_id > rating.rating_id // many-to-one
Ref: films_genres.film_id > films.film_id // many-to-one
Ref: genres.genre_id > films_genres.genre_id // many-to-one
```


## Query Examples
### Getting Film names and genres for user id = 13

```roomsql

select u.user_name,0
    f.film_name,
    g.genre_name        
 from users u 
join likes l on u.user_id = l.user_id 
join films f on f.film_id = l.film_id    
join films_genres fg on f.film_id = fg.film_id
join genres g on fg.genre_id = g.genre_id
where u.user_id = 13;

```
