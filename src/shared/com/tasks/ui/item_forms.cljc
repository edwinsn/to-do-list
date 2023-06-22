(ns com.tasks.ui.item-forms
  (:require
    [com.tasks.model.item :as item]
    [com.fulcrologic.rad.form :as form]
    [com.fulcrologic.rad.form-options :as fo]
    [com.fulcrologic.rad.report :as report]
    [com.fulcrologic.rad.report-options :as ro]
    [com.fulcrologic.fulcro.components :as comp]
    [com.fulcrologic.rad.form :as form]
    [com.tasks.model.item :as model]
    #?(:cljs [com.fulcrologic.fulcro.dom :as dom ])
    ))

(defn handleTaskStatusUpdate [this status id]
  #?(:cljs
     (comp/transact! this [(model/update-item-status {:id id :status (not status) } )])
  )
  )


(form/defsc-form ItemForm [this props]
  {fo/id            item/id
   fo/attributes    [item/item-name
                     item/status]
   fo/cancel-route      ["tasks"]
   fo/route-prefix   "tasks"
   fo/title         "Task"})

(report/defsc-report TasksReport [this props]
  {ro/title               "Steps to happiness"
   ro/source-attribute    :item/all-items
   ro/row-pk              item/id
   ro/columns             [item/item-name
                           item/status
                           ]
   ro/initial-sort-params {:sort-by          :item/name
                           :sortable-columns #{:item/name}
                           }
   ro/form-links          {item/item-name ItemForm}
   ro/route               "tasks"
   ro/run-on-mount?            true
   ro/controls             {::new-item {:label  "New Task"
                                       :type   :button
                                       :action (fn [this] (form/create! this ItemForm))}
                            }
   ro/column-formatters  {
                          :item/status
                          (fn [this status {:keys [item/id]}]
                            (dom/input {:type           "checkbox"
                                        :defaultChecked status
                                        :onChange #(handleTaskStatusUpdate this status id) ;#(form/edit! this FormClass id)
                                        })
                            )
                          :item/name
                          (fn [_ name ]
                            (dom/span (or name "Unnamed Task"))
                            )
                          }

   ro/row-actions         [
                           {
                            :type :button
                            :action  (fn [report-instance {:keys [item/id]}] (form/delete! report-instance :item/id id))
                            :label (fn [] "x")
                            }
                           ]
   })