import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import {Row} from "reactstrap";
import ProjectsTitlesComponent from "../AddNewIssue/ProjectsTitlesComponent";

class AddNote extends Component {
    constructor(props){
        super(props)
        this.state = {
            note: {
                selectedDate: 0,
                project: this.props.t('prompts.project', { framework: "react-i18next" }),
                inscription: this.props.t('prompts.note', {framework: "react-i18next"})
            }
        }
    }
    componentDidMount(): void {
        const note = this.state.note
        note.selectedDate = this.props.selectedDate
        this.setState({ note: note })
    }

    handleChange(propertyName, event) {
        const note = this.state.note
        note[propertyName] = event.target.value
        this.setState({ note: note })
    }
    myCallback = (dataFromChild) => {
        const note = this.state.note
        note.project = dataFromChild
        this.setState({ note: note })
    }
    render(){
        return(
            <div>
                <Row>
                    <ProjectsTitlesComponent callbackFromParent={this.myCallback} projects={this.props.projects}/>
                </Row>
                <Row>
                    <textarea rows={5} cols={35} type="text" onChange={this.handleChange.bind(this, 'inscription')} value={this.state.note.inscription}/>
                </Row>
                <Row>
                    <button className={this.props.buttonStyle} onClick={()=>{
                        fetch('http://localhost:8080/calendar/entry/' + this.state.project, {
                            method: 'POST',
                            headers: {
                                'Accept': 'application/json',
                                'Content-Type': 'application/json',
                                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                            },
                            body: JSON.stringify(
                                this.state.note)
                        })
                    }}>
                        {this.props.t('buttons.submit', { framework: "react-i18next" })}
                    </button>
                </Row>
            </div>
        )
    }
}
export default translate('common')(AddNote);