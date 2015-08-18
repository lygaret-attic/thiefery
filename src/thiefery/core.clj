(ns thiefery.core
  (:require [thiefery.state :as state]
            [lanterna.screen :as screen]))

(defmethod state/draw :boot [state scr]
  (screen/put-string scr 10 10 "Welcome to THIEFERY.")
  (screen/put-string scr 10 11 "A game of thiefing, adventure and glory.")
  (screen/put-string scr 10 12 "Press enter to win."))

(defmethod state/run :boot [state scr stack]
  (let* [key (screen/get-key-blocking scr)
         next (if (= key :enter)
                (state/make-state :win {:how "By being awesome!"})
                (state/make-state :lose {:how "By not following instructions."}))]
        (conj stack next)))

(defmethod state/draw :win [state scr]
  (screen/put-string scr 10 10 "You won THIEFERY!")
  (screen/put-string scr 10 11 (get-in state [:params :how]))
  (screen/put-string scr 10 12 "Press any key to exit."))

(defmethod state/run :win [state scr stack]
  (screen/get-key-blocking scr)
  stack)

(defmethod state/draw :lose [state scr]
  (screen/put-string scr 10 10 "You lost THIEFERY!")
  (screen/put-string scr 10 11 (get-in state [:params :how]))
  (screen/put-string scr 10 12 "Press any key to exit."))

(defmethod state/run :lose [state scr stack]
  (screen/get-key-blocking scr)
  stack)

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
      (game-loop scr [(state/make-state :boot)]))))

(defn -main [& args]
  (let [args (set args)
        type (cond
               (args "swing") :swing
               (args "text")  :text
               :else          :auto)]
    (main type)))
