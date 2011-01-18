(ns mutt.core)

(defn- sign [x]
  (cond
   (neg? x) -1
   (pos? x) 1
   :else 0))

(defprotocol Rangeable
  (first-item [_])
  (after? [a b])
  (next-item [prev step]))

(extend-protocol Rangeable
  Number
  (first-item [_] 0)
  (after? [a b] (>= a b))
  (next-item
   ([prev step]
      (+ prev step)))
  
  Character
  (first-item [_] Character/MIN_VALUE)
  (after? [a b] (>= (int a) (int b)))
  (next-item
   ([prev step]
      (char (+ (int step) (int prev))))))

(defn range
  ([] (range 0 Double/POSITIVE_INFINITY 1 true))
  ([end] (range (first-item end) end 1 true))
  ([start end] (range start end 1 true))
  ([start end step] (range start end step true))
  ([start end step exc?]
     (let [s (sign step)
           end (if exc? end (next-item end s))
           after? (if (= 1 s)
                   after?
                   #(after? %2 %1))]
       ((fn range* [item]
          (lazy-seq
           (when-not (after? item end)
             (cons item
                   (range* (next-item item step))))))
        start))))
