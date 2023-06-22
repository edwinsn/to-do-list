(ns com.tasks.model.item
  (:require
    [com.fulcrologic.rad.attributes :refer [defattr]]
    [com.fulcrologic.rad.attributes-options :as ao]
    [com.wsscode.pathom.connect :as pc]
    [com.fulcrologic.rad.form :as form]

    #?@(:clj
        [[com.wsscode.pathom.connect :as pc :refer [defmutation]]
         [com.tasks.components.database-queries :as queries]]
        :cljs
        [[com.fulcrologic.fulcro.mutations :as m :refer [defmutation]]])
    ))

(defattr id :item/id :uuid
  {ao/identity? true
   ao/schema    :production})


(defattr item-name :item/name :string
  {ao/identities #{:item/id}
   ao/schema     :production})

(defattr description :item/description :string
  {ao/identities #{:item/id}
   ao/schema     :production})

(defattr status :item/status :boolean
  {ao/identities #{:item/id}
   ao/schema     :production})

(defattr dificulty :item/dificulty :string
         {ao/identities #{:item/id}
          ao/schema     :production}
         )


(defattr all-items :item/all-items :ref
  {ao/target    :item/id
   ao/cardinality :many
   ::pc/output  [{:item/all-items [:item/id]}]
   ::pc/resolve (fn [{:keys [query-params] :as env} _]
                  #?(:clj
                     {:item/all-items (queries/get-all-tasks env )}
                     )
                  )})

#?(:clj
   (defmutation update-item-status [env {
                                         id   :id
                                         status :status
                                         }]
                {::pc/params #{:item/id}
                 ::pc/output [:item/id]}
                (form/save-form* env {::form/id        id
                                      ::form/master-pk :item/id
                                      ::form/delta     {[:item/id id] {:item/status {:before (not status) :after (boolean status)}}}}
                                 ))
   )

;
#?(
   :cljs
   (defmutation update-item-status [{
                                     id   :id
                                     status :status
                                     }]
                (action [{:keys [state]}]
                        (swap! state assoc-in [:item/id id :item/status] status)
                        )
                (remote [_] true)
                )
   )


(def attributes [id item-name description status dificulty all-items])

#?(:clj
   (def resolvers [update-item-status])
   )