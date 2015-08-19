(ns thiefery.map
  (:require [thiefery.tileset :as tiles]))

(defn- random-tile
  "Returns a random tile from the given tileset."
  [tileset]
  (rand-nth (keys tileset)))

(defn- random-row
  "Returns a randomly generated vector of tiles, of the given length"
  [len tileset]
  (vec (repeatedly len #(random-tile tileset))))

(defn random-map
  "Gets a new random tilemap with the given bounds"
  [rows cols]
  {:bounds [rows cols]
   :tiles  (vec (repeatedly cols #(random-row rows tiles/landscape)))})

(defn tile-at
  "Gets the tile at the x,y location in the given tilemap"
  [x y tiles]
  (get-in tiles [y x] tiles/oob))
