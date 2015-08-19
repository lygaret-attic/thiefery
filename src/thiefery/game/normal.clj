(ns thiefery.game.normal
  (:require [thiefery.state  :as state]
            [thiefery.map    :as map]
            [lanterna.screen :as screen]))

(defmethod state/draw :normal [state scr]
  (let [{[rows cols] :bounds world :world} (get-in state [:params :world])]
    (doseq [row (range 0 rows)
            col (range 0 cols)]
      (let [tile (map/tile-at row col world)]
        (screen/put-string scr row col (:char tile) tile)))))

(defmethod state/run :normal [state scr stack]
  (let [key (screen/get-key-blocking scr)
        how (if (= key :enter)
              "Won by being awesome!"
              "Lost, By not following instructions!")]
    (conj stack (state/make-state :endgame {:how "By not following instructions!"}))))
