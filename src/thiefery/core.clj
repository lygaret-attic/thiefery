(ns thiefery.core
  (:require [thiefery.state  :as state]
            [thiefery.map    :as map]
            [lanterna.screen :as screen]))

(require 'thiefery.game.boot
         'thiefery.game.normal
         'thiefery.game.endgame)

(defn- game-cycle
  "Run one iteration of the game."
  [scr stack]
  (screen/clear scr)
  (doseq [x stack] (state/draw x scr))
  (screen/redraw scr)
  (state/run (peek stack) scr (pop stack)))

(defn- game-loop
  "Run the game to completion."
  [scr boot]
  (loop [stack boot]
    (when (seq stack)
      (recur (game-cycle scr stack)))))

(defn- main [type]
  (let [scr (screen/get-screen type)]
    (screen/in-screen scr
      (game-loop scr [(state/make-state :boot )]))))

(defn -main [& args]
  (let [args (set args)
        type (cond
               (args "swing") :swing
               (args "text")  :text
               :else          :auto)]
    (main type)))
