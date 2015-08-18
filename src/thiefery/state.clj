(ns thiefery.state
  (:require [lanterna.screen :as screen]))

(defn make-state
  ([name]
   (make-state name {}))
  ([name params]
   {:name name :params params}))

(defmulti draw
  "Draw this state's UI"
  (fn [state scr]
    (:name state)))

(defmulti run
  "Executes whatever code takes place at this state, side effects and input, and returns a new state stack."
  (fn [state scr stack]
    (:name state)))

(defmethod draw :default
  [state scr]
  (do
    (screen/put-string scr 10 9  "!!!")
    (screen/put-string scr 10 10 (str "Unknown state: " state))
    (screen/put-string scr 10 10 "!!!")))

(defmethod run :default
  [state scr stack]
  (screen/get-key-blocking scr)
  [])
