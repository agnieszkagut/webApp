import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import TasksListComponent from "./TasksListComponent";
import EditTasksComponent from "./EditTasksComponent";

class TasksComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            showTasks: false,
            showEdit: false
        }
    }
    render(){
        return(
            <div>
                <div>
                    {this.props.t('project.tasksDone', { framework: "react-i18next" })}{this.props.numberOfTasksDone}/{this.props.numberOfAllTasks}
                    <i className="fas fa-chevron-down" onClick={()=> this.setState({showTasks: !this.state.showTasks})}></i>
                </div>
                <div>
                    {this.state.showTasks
                        ?(this.props.numberOfAllTasks
                            ?<TasksListComponent listOfTasks={this.props.listOfTasks}
                                                 numberOfDelayed={this.props.numberOfDelayed}
                                                 credentials={this.props.credentials}
                                                 projectId={this.props.projectId}
                                                 projectId2={this.props.projectId2}
                                                 user={this.props.user}
                        />
                        :"<none>")
                        :null
                    }
                </div>
                {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0 || (this.props.credentials.userLevel === 2 && this.props.projectId2==this.props.projectId))
                    ?<div>
                    <button className={this.props.buttonStyle} onClick={()=> this.setState({showEdit: !this.state.showEdit})}>
                        {this.props.t('buttons.edit', { framework: "react-i18next" })}
                    </button>
                </div>
                    :null
                }
                <div>
                    {this.state.showEdit
                        ?<EditTasksComponent projectId={this.props.projectId}
                                             buttonStyle={this.props.buttonStyle}
                                             credentials={this.props.credentials}
                        />
                        :null
                    }
                </div>
            </div>
        )
    }
}

export default translate('common')(TasksComponent);