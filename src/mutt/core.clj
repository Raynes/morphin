(ns mutt.core)

(defprotocol Rangeable
  (first-item [_])
  (after [item])
  (next-item [prev end step]))

(extend-protocol Rangeable
  Number
  (first-item [_] 0)
  (after [num] (inc num))
  (next-item
   ([prev end step]
      (let [res (+ prev step)]
        (when (< res end)
          res))))
  
  Character
  (first-item [_] Character/MIN_VALUE)
  (after [c] (char (inc (int c))))
  (next-item
   ([prev end step]
      (let [res (+ (int step) (int prev))]
        (when (< res (int end))
          (char res))))))

(defn range
  ([] (range 0 Double/POSITIVE_INFINITY 1 true))
  ([end] (range (first-item end) end 1 true))
  ([start end] (range start end 1 true))
  ([start end step] (range start end step true))
  ([start end step exc?]
     (let [end (if exc? end (after end))]
       ((fn range* [item]
          (lazy-seq
           (cons item
                 (when-let [res (next-item item end step)]
                   (range* res)))))
        start))))
