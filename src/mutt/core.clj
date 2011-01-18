(ns mutt.core)

(defprotocol Rangeable
  (prange [end exc?] [start end exc?] [start end step exc?]))

(extend-type Number
  Rangeable
  (prange
   ([end exc?] (prange 0 end 1 exc?))
   ([start end exc?] (prange start end 1 exc?))
   ([start end step exc?]
      (lazy-seq
       (let [comparison (if (pos? step) >= <=)]
         (when-not (comparison start (if exc? end (inc end)))
           (let [inc-fn (if-not (= step 0) (partial + step) inc)]
             (cons start (prange (inc-fn start) end step exc?)))))))))

(extend-type Character
  Rangeable
  (prange
   ([end exc?] (prange (Character/MIN_VALUE) end 1 exc?))
   ([start end exc?] (prange start end 1 exc?))
   ([start end step exc?]
      (map char (prange (int start) (int end) step exc?)))))

(defn range
  ([] (prange 0 (Double/POSITIVE_INFINITY) 1 true))
  ([end] (prange end true))
  ([start end] (prange start end 1 true))
  ([start end step] (prange start end step true))
  ([start end step exclusive?] (prange start end step exclusive?)))