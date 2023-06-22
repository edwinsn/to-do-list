(ns com.tasks.client
  (:require
    [com.tasks.ui :refer [Root]]
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.components :as comp]
    [com.fulcrologic.fulcro.routing.dynamic-routing :as dr]
    [com.fulcrologic.rad.application :as rad-app]
    [com.fulcrologic.rad.rendering.semantic-ui.semantic-ui-controls :as sui]
    [com.fulcrologic.rad.report :as report]
    [com.fulcrologic.rad.routing.history :as history]
    [com.fulcrologic.rad.routing.html5-history :as hist5 :refer [new-html5-history]]
    [taoensso.tufte :as tufte :refer [profile]]))

(defonce stats-accumulator
  (tufte/add-accumulating-handler! {:ns-pattern "*"}))


(defn setup-RAD [app]
  (rad-app/install-ui-controls! app sui/all-controls)
  (report/install-formatter! app :boolean :affirmation (fn [_ value] (if value "yes" "no"))))

(defonce app (rad-app/fulcro-rad-app {}))

(defn refresh []
  (setup-RAD app)
  (comp/refresh-dynamic-queries! app)
  (app/force-root-render! app))

(defn init []
  (app/set-root! app Root {:initialize-state? true})
  (setup-RAD app)
  (dr/change-route! app ["tasks"])
  (history/install-route-history! app (new-html5-history {:app           app
                                                          :default-route {:route ["tasks"]}}))
  (app/mount! app Root "app" {:initialize-state? false}))

(defonce performance-stats (tufte/add-accumulating-handler! {}))