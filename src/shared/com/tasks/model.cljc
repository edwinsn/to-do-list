; Hi Daniel, I just want to say I did not export the model from the tasks model
; because I guess in real word apps our app models will have arributes from many sources,
; not just from an object (in this case a task)
(ns com.tasks.model
  (:require
    [com.tasks.model.item :as item]
    [com.fulcrologic.rad.attributes :as attr]))

(def all-attributes  item/attributes)
(def all-attribute-validator (attr/make-attribute-validator all-attributes))