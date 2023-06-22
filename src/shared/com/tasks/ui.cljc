(ns com.tasks.ui
  (:require

    #?(:cljs [com.fulcrologic.fulcro.dom :as dom :refer [div]])
    [com.tasks.ui.item-forms :refer [ItemForm TasksReport]]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.routing.dynamic-routing :refer [defrouter]]
    [com.fulcrologic.rad.routing :as rroute]))


;Router
(defrouter MainRouter [_ _]
  {
   :router-targets      [TasksReport ItemForm]
   :initial-route  [TasksReport]

   })

(def ui-main-router (comp/factory MainRouter))


(defsc Root [this {:keys [router]}]
  {
   :query [
           {
              :router (comp/get-query MainRouter)
            }
           ]
   :initial-state {:router {}}
   }
  (dom/div
    (div :.ui.top.menu
         (comp/fragment
           (div :.ui.item
                (div {:onClick (fn [] (rroute/route-to! this TasksReport {}))} "All Tasks")
                )
           )
         )
    (div :.ui.container.segment
         (ui-main-router router))
    )
  )


(def ui-root (comp/factory Root))

