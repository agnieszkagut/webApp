import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class EditTasksComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            task: {
                name: this.props.t('prompts.taskName', {framework: "react-i18next"}),
                deadline: new Date()
            }
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.pickDate = this.pickDate.bind(this);
    }
    handleInputChange(event) {
        const task = this.state.task
        task.name = event.target.value
        this.setState({ task: task })
    }
    handleChange(date) {
        const task = this.state.task
        task.deadline = date
        this.setState({ task: task })
    }

    pickDate() {
        return (
            <DatePicker
                selected={this.state.task.deadline}
                onChange={this.handleChange}
            />
        )
    }
    onSubmit(){
                fetch('http://localhost:8080/projects/' + this.props.projectId + "/task", {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                },
                body: JSON.stringify(
                    this.state.task)
            })
        }
    render(){
        return(
            <div>
                <div>
                    <input type="text" onChange={this.handleInputChange} value={this.state.task.name}/>
                </div>
                <div>
                    {this.pickDate()}
                </div>
                <div>
                    <button className={this.props.buttonStyle} onClick={()=>{
                        this.onSubmit()}}>
                        {this.props.t('buttons.submit', { framework: "react-i18next" })}
                    </button>
                </div>
            </div>
        )
    }
}

export default translate('common')(EditTasksComponent);