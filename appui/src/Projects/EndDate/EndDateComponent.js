import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import Moment from 'react-moment';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

class EndDateComponent extends Component{
    constructor(props){
        super(props)
        this.state={
            showEdit: false,
            endDate: new Date()
        }
        this.handleChange = this.handleChange.bind(this);
        this.pickDate = this.pickDate.bind(this);
    }
    handleChange(date) {
        this.setState({
            endDate: date
        });
    }
    pickDate(){
        return(
            <div>
                <DatePicker
                    selected={this.state.endDate}
                    onChange={this.handleChange}
                />
                <button className={this.props.buttonStyle} onClick={()=>{
                    fetch('http://localhost:8080/projects/endDate/' + this.props.projectId, {
                        method: 'PUT',
                        headers: {
                            'Accept': 'application/json',
                            'Content-Type': 'application/json',
                            'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
                        },
                        body: JSON.stringify(
                            this.endDate)
                    })
                        .then(this.setState({showEdit: !this.state.showEdit}))
                }}>
                    {this.props.t('buttons.submit', { framework: "react-i18next" })}
                </button>
            </div>
        )
    }
    render(){
        return(
            <div>
                <div>
                    {this.props.t('project.endDate', { framework: "react-i18next" })}
                    <Moment format="YYYY-MM-DD HH:mm">
                        {this.props.endDate}
                    </Moment>
                    {(this.props.credentials.userLevel === 1 || this.props.credentials.userLevel === 0)
                    ?<i className="far fa-calendar-alt" onClick={()=> this.setState({showEdit: !this.state.showEdit})}></i>
                     :null}
                </div>
                {this.state.showEdit
                    ?this.pickDate()
                    :null
                }
                <div>

                </div>
            </div>
        )
    }
}

export default translate('common')(EndDateComponent);