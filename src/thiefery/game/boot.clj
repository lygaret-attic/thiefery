(ns thiefery.game.boot
  (:require [thiefery.state  :as state]
            [thiefery.map    :as map]
            [lanterna.screen :as screen]))

(defmethod state/draw :boot [state scr]
  (screen/put-string scr 10 10 "Welcome to THIEFERY!")
  (screen/put-string scr 10 11 "A game of intrugue and thiefing!")
  (screen/put-string scr 10 12 "Press any key to begin!"))

(defmethod state/run :boot [state scr stack]
  (screen/get-key-blocking scr)
  (conj stack (state/make-state :normal {:world (map/random-map 25 25)})))
