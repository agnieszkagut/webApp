import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import NumberFormat from 'react-number-format';

class AddSponsorshipsComponent extends Component{
    constructor(props){
        super(props)
        this.state= {
            newSponsorship: {
                projectId: 0,
                name: this.props.t('prompts.fundName', {framework: "react-i18next"}),
                value: ""
            }
        }
        this.onSubmit = this.onSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleNumber = this.handleNumber.bind(this);
    }
    componentDidMount(): void {
        const newSponsorship = this.state.newSponsorship
        newSponsorship.projectId = this.props.projectId
        this.setState({ newSponsorship: newSponsorship })
    }

    handleNumber(fund){
        const newSponsorship = this.state.newSponsorship
        newSponsorship.value = fund
        this.setState({ newSponsorship: newSponsorship })
    }
    onSubmit(){
        fetch('http://localhost:8080/projects/' + this.state.newSponsorship.projectId + "/sponsorship", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            },
            body: JSON.stringify(
                this.state.newSponsorship.name,
                this.state.newSponsorship.value)
        })
    }
    handleChange(event) {
        const newSponsorship = this.state.newSponsorship
        newSponsorship.name = event.target.value
        this.setState({ newSponsorship: newSponsorship })
    }
    render(){
        return(
            <div>
                <div>
                    {this.props.t('prompts.fundName', { framework: "react-i18next" })}
                    <input type="text" onChange={this.handleChange} value={this.state.name}/>
                </div>
                <div>
                    {this.props.t('project.sponsorship', { framework: "react-i18next" })}
                    <NumberFormat
                        onValueChange={(values) => this.handleNumber(values.formattedValue)}
                    />
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

export default translate('common')(AddSponsorshipsComponent);