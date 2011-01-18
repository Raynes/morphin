(ns mutt.core-test
  (:refer-clojure :exclude [range])
  (:require [clojure.core :as c])
  (:use mutt.core
        clojure.test))

(deftest compat-test
  (is (= (c/range 5)
         (range 5)))
  (is (= (c/range 5 10)
         (range 5 10)))
  (is (= ()
         (range 10 5)))
  (is (= (c/range 20 5 -1)
         (range 20 5 -1)))
  (is (= (c/range 10 30 2)
         (range 10 30 2))))

(deftest excl-test
  (is (= [0 1 2]
           (range 0 3 1 true)
           (range 0 2 1 false)))
  (is (= [2 1 0]
           (range 2 -1 -1 true)
           (range 2 0 -1 false))))

(deftest char-test
  (is (= [\a \b \c]
           (range \a \d)
           (take 3 (range \a \z))
           (range \a \c 1 false)))
  (is (= [\A \C \E \G]
           (range \A \G 2 false))))
