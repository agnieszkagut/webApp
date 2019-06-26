import React, { Component } from "react";
import {Row} from "reactstrap";
import ProjectsTitlesComponent from "./ProjectsTitlesComponent";
import { translate, Trans } from 'react-i18next';

class AddIssue extends  Component{
    constructor(props) {
        super(props);
        this.state = {
            issue:{
                user: 0,
                projectId: this.props.t('prompts.project', { framework: "react-i18next" }),
                title: this.props.t('prompts.title', { framework: "react-i18next" }),
                inscription: this.props.t('prompts.inscription', { framework: "react-i18next" })
            },
            projects: []
        };
        this.handleChange = this.handleChange.bind(this);
    }
    componentDidMount() {
        const URL = "http://localhost:8080"
        const issue = this.state.issue
        issue.user = this.props.user
        this.setState({ issue: issue })
        fetch(URL + "/projects",{
            headers:{
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then(res => res.json())
            .then(data => {
                this.setState({ projects: data })
            })
    }
    handleChange(propertyName, event) {
        const issue = this.state.issue
        issue[propertyName] = event.target.value
        this.setState({ issue: issue })
    }
    myCallback = (dataFromChild) => {
        const issue = this.state.issue
        issue.projectId = dataFromChild
        this.setState({ issue: issue })
    }
    render(){
        return (
            <div>
                <h3>
                    {this.props.t('prompts.addNewIssue', {framework: "react-i18next"})}
                </h3>
                <div>
                    <Row>
                        <ProjectsTitlesComponent callbackFromParent={this.myCallback} projects={this.state.projects}/>
                    </Row>
                    <Row>
                        <input type="text" onChange={this.handleChange.bind(this, 'title')} value={this.state.issue.title}/>
                    </Row>
                    <Row>
                        <textarea rows={10} cols={50} type="text" onChange={this.handleChange.bind(this, 'inscription')} value={this.state.issue.inscription}/>
                    </Row>
                    <Row>
                        <button className={this.props.buttonStyle} onClick={()=>{
                            fetch('http://localhost:8080/issues', {
                                method: 'POST',
                                headers: {
                                    'Accept': 'application/json',
                                    'Content-Type': 'application/json',
                                    'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                                },
                                body: JSON.stringify(
                                    this.state.issue)
                            })}}>
                            {this.props.t('buttons.submit', { framework: "react-i18next" })}
                        </button>
                    </Row>
                </div>
            </div>
        )
    }
}
export default translate('common')(AddIssue);