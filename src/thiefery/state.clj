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

(defmethod draw :default [state scr])

(defmulti run 
  "Executes whatever code takes place at this state, side effects 
   and input, and returns a new state stack."
  (fn [state scr stack]
    (:name state)))

(defmethod run :default [state scr stack]
  stack)
