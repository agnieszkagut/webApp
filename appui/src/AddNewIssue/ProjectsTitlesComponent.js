import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';

class ProjectsTitlesComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedProject: ''
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({selectedProject: event.target.value})
        this.props.callbackFromParent(event.target.value)
    }
    render () {
        console.log(this.state.selectedProject)
        let optionItems = this.props.projects.map((project, index) =>
            <option key={index} value={project.projectId}> {project.name}</option>
        )
        const firstOne = <option key='key' value=''> {this.props.t('prompts.project', { framework: "react-i18next" })}</option>
        optionItems.unshift(firstOne)
        return (
            <div>
                <select value={this.state.selectedProject} onChange={this.handleChange}>
                    {optionItems}
                </select>
            </div>
        )
    }
}
export default translate('common')(ProjectsTitlesComponent);