import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import TaskItem from "./TaskItem";

class TasksListComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            refresher: true
        }
        this.onCheck = this.onCheck.bind(this);
    }
    onCheck(task){
        fetch('http://localhost:8080/projects/tasks/' + task.taskId + "/done", {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            },
            body: JSON.stringify(
                !task.isDone)
        })
        this.setState({refresher: !this.state.refresher})
    }
    render(){
        const onCheck = this.onCheck.bind(this);
        const credentials = this.props.credentials
        const user = this.props.user
        const projectId = this.props.projectId
        const projectId2 = this.props.projectId2
        console.log(this.props.credentials.userLevel)
        console.log(this.props.credentials.userLevel === 2)
        console.log(this.props.projectId2)
        console.log(this.props.projectId)
        console.log(this.props.projectId2 === this.props.projectId)
        console.log((this.props.credentials.userLevel === 2 && this.props.projectId2 === this.props.projectId))
        return(
            <div>
                {this.props.t('project.delayed', { framework: "react-i18next" })} {this.props.numberOfDelayed}
                <ul>
                    {this.props.listOfTasks.map(function(task,index) {
                            return (
                                <div key={index}
                                    style={{display:'inline-flex'}}>
                                    {(credentials.userLevel === 1 || credentials.userLevel === 0 || (credentials.userLevel === 2 && projectId2 === projectId))
                                            ?(task.isDone ? <i className="fas fa-check-square" style={{color:"green"}} onClick={()=>{
                                                task.isDone = !task.isDone
                                                onCheck(task)}}></i>
                                            : <i className="far fa-check-square" onClick={()=>{
                                                task.isDone = !task.isDone
                                                onCheck(task)}}></i>
                                        )
                                    :null
                                    }
                                    <TaskItem
                                            name={task.name}
                                            deadline={task.deadline}
                                        />
                                </div>
                            )
                        }
                    )}
                </ul>
            </div>
        )
    }
}

export default translate('common')(TasksListComponent);