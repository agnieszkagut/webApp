import React, { Component } from "react";
import IssueComponent from "./IssueComponent";
import { translate, Trans } from 'react-i18next';

class ReportedIssues extends  Component{
    constructor(props) {
        super(props);
        this.state = {issues: []};
    }
    componentDidMount() {
        const URL = "http://localhost:8080"
        fetch(URL + "/issues", {
            headers: {
            'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }})
            .then(res => res.json())
            .then(data => {
                this.setState({ issues: data })
            });
    }
    render(){
        const credentials=this.props.credentials
        const buttonStyle = this.props.buttonStyle
        return (
            <ul>
                {this.state.issues.map(function(issue,index) {
                        return (
                            <div key={index}>
                                <IssueComponent issueId={issue.issueId}
                                                status={issue.status}
                                                title={issue.title}
                                                key={index}
                                                buttonStyle={buttonStyle}
                                                credentials={credentials}
                                />
                            </div>
                        )
                    }
                )}
            </ul>
        )
    }
}

export default translate('common')(ReportedIssues);