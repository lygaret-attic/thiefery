(ns thiefery.game.endgame
  (:require [thiefery.state  :as state]
            [thiefery.map    :as map]
            [lanterna.screen :as screen]))

(defmethod state/draw :endgame [state scr]
  (let [how (get-in state [:params :how])]
    (screen/put-string scr 10 10 "How'd we do?")
    (screen/put-string scr 10 11 how)
    (screen/put-string scr 10 11 "Awesome! Bye now!")))

(defmethod state/run :endgame [state scr stack]
  (screen/get-key-blocking scr)
  stack)
