(ns morphin.core-test
  (:use midje.sweet)
  (:require [morphin.core :as m]))

(fact "Integer range is just like the clojure.core version"
  (m/range 5) => (just (range 5))
  (m/range 5 10) => (just (range 5 10))
  (m/range 10 5) => ()
  (m/range 20 5 -1) => (just (range 20 5 -1))
  (m/range 10 30 2) => (just (range 10 30 2)))

(fact "Can be exclusive."
  (m/range 0 3 1 true) => (just [0 1 2])
  (m/range 2 -1 -1 true) => (just [2 1 0]))

(fact "Can be inclusive."
  (m/range 0 2 1 false) => (just [0 1 2])
  (m/range 2 0 -1 false) => (just [2 1 0]))

(fact "Can create a range of characters."
  (m/range \a \d) => (just [\a \b \c])
  (m/range \a \z) => (has-prefix [\a \b \c])
  (m/range \a \c 1 false) => (just [\a \b \c])
  (m/range \A \G 2 false) => (just [\A \C \E \G]))
