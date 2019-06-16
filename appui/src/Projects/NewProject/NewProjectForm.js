import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import NumberFormat from 'react-number-format';
import UsersComponent from "../../Messages/UsersComponent";

class NewProjectForm extends Component{
    constructor(props){
        super(props)
        this.state={
            project:{
                name: "",
                leaderEmail: "",
                description: this.props.t('prompts.description', { framework: "react-i18next" }),
                sponsorship: 0,
                endDate: new Date()
            },
            listOfLeaders: []
        }
        this.onCancel = this.onCancel.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleDateChange = this.handleDateChange.bind(this);
        this.pickDate = this.pickDate.bind(this);
        this.myCallback = this.myCallback.bind(this);
        this.handleNumberChange = this.handleNumberChange.bind(this);

    }
    componentDidMount(): void {
        const URL = "http://localhost:8080"
        fetch(URL + "/projects/leaders/",{headers:{
            'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then(res => res.json())
            .then(data => {
                this.setState({ listOfLeaders: data })
            });
    }

    handleDateChange(date) {
        const project = this.state.project
        project.endDate = date
        this.setState({ project: project })
    }
    handleNumberChange(fund ) {
        const project = this.state.project
        project.sponsorship = fund
        this.setState({ project: project })
    }
    handleChange(propertyName, event) {
        const project = this.state.project
        project[propertyName] = event.target.value
        this.setState({ project: project })
    }
    pickDate() {
        return (
            <DatePicker
                selected={this.state.project.endDate}
                onChange={this.handleDateChange}
            />
        )
    }
    myCallback = (dataFromChild) => {
        const project = this.state.project
        project.leaderEmail = dataFromChild
        this.setState({ project: project })
    }
    onCancel(){
        this.props.callbackFromParent()
    }
    onSubmit(){
        fetch('http://localhost:8080/projects', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            },
            body: JSON.stringify(
                this.state.project)
        })
        this.props.callbackFromParent()
    }
    render(){
        return(
            <div>
                <form className="form-horizontal">
                    <fieldset>
                        <legend>{this.props.t('prompts.newProject', {framework: "react-i18next"})}</legend>
                <div >
                    <label className="control-label" htmlFor="inputDefault">{this.props.t('prompts.projectName', {framework: "react-i18next"})}</label>
                    <input type="text"  onChange={this.handleChange.bind(this, 'name')} value={this.state.project.name}/>
                </div>
                <div>
                    {this.props.t('project.projectLeader', { framework: "react-i18next" })}
                    <UsersComponent callbackFromParent={this.myCallback} users={this.state.listOfLeaders}/>
                </div>
                <div>
                    <textarea rows={10} cols={50} type="text" onChange={this.handleChange.bind(this, 'description')} value={this.state.project.description}/>
                </div>
                <div>
                    {this.props.t('project.sponsorship', { framework: "react-i18next" })}
                    <NumberFormat
                        defaultValue={this.state.sponsorship}
                        onValueChange={(values) => this.handleNumberChange(values.formattedValue)}
                    />
                </div>
                <div>
                    {this.props.t('project.endDate', { framework: "react-i18next" })}
                    {this.pickDate()}
                </div>
                <div>
                    <button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=>{
                        this.onSubmit()}}>
                        {this.props.t('buttons.submit', { framework: "react-i18next" })}
                    </button>
                    <button className={this.props.buttonStyle} style={{border: "1px solid white"}} onClick={()=>{
                        this.onCancel()}}>
                        {this.props.t('buttons.cancel', { framework: "react-i18next" })}
                    </button>
                </div>
                    </fieldset>
                </form>
            </div>
        )
    }
}

export default translate('common')(NewProjectForm);