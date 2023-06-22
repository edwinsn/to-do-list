(ns com.tasks.components.database-queries
  (:require
    [com.fulcrologic.rad.database-adapters.datomic-options :as do]
    [datomic.client.api :as d]
    [taoensso.timbre :as log]
    ))

(defn- env->db [env]
  (some-> env (get-in [do/databases :production]) (deref)))

(defn get-all-tasks
      [env]
      (if-let [db (env->db env)]
              (let [results (d/q '[:find (pull ?e [:item/id])
                                   :where
                                   [?e :item/id]] db)]
                   (mapv first results))

              (log/error "No database atom for production schema!")))

;;Not implemented queries

;The tasks
;[:find (pull ?t [*])
; :in $ ?user
; :where [?t :task/name ?task]
;        [?t :task/user ?user]
; ]


;Retrieve all completed tasks STATUS for a specific user:
;[:find (pull ?t [{:task/_user [:user/name :user/last-name] }} ])
; :in $ ?user
; :where [?t :task/id ?task]
; [?t :task/user ?user]
; [?t :task/status true]

; ]

;What does ?t return, and how to get all the data.
;Use pull

;Retrieve all incomplete tasks names for a specific user:
;[:find ?task
; :in $ ?user
; :where [?t :task/name ?task]
; [?t :task/user ?user]
; [?t :task/status false]]


;Add a new task for a specific user:
;[:db/add (d/tempid :db.part/user) :task/name "New Task"
; :task/user user-id
; :task/status false]


;Update the status of a task (e.g., mark it as complete):
;[:db/add task-id :task/status true]