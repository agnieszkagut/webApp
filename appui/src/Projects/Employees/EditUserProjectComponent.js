import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import UsersComponent from "../../Messages/UsersComponent";

class EditUserProjectComponent extends Component{
    constructor(props){
        super(props)
        this.state= {
            change: {
                user: this.props.t('prompts.user', {framework: "react-i18next"}),
                project: ""
            }
        }
        this.myCallback = this.myCallback.bind(this);
        this.mySecondCallback = this.mySecondCallback.bind(this);
    }
    componentDidMount(): void {
        this.setState({project: this.props.project})
    }

    myCallback = (dataFromChild) => {
        const change = this.state.change
        change.user = dataFromChild
        this.setState({ change: change })
    }
    mySecondCallback = (dataFromChild) => {
        const change = this.state.change
        change.project = dataFromChild
        this.setState({ change: change })
    }
    onSubmit(){
        fetch('http://localhost:8080/projects/user', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            },
            body: JSON.stringify(
                this.state.change)
        })
    }
    render(){
        return(
            <div>
                <div>
                    <UsersComponent callbackFromParent={this.myCallback} users={this.props.users}/>
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

export default translate('common')(EditUserProjectComponent);