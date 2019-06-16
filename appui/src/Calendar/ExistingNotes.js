import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import CircularProgress from 'material-ui/CircularProgress';
import NotesListComponent from "./NotesListComponent";
import moment from 'moment';

class ExistingNotes extends Component {
    constructor(props){
        super(props)
        this.state={
            notes: [],
            emptyFlag: true,
            loading: true
        }
    }
    componentDidMount(): void {
        console.log(this.props.date.day)
        fetch(URL + "/calendar/entries/"  + this.props.date.day, {
            headers:{
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then((response) => {
            if(!response.ok) throw new Error(response.status);
            else return response.json();
        })
            .then(data => {
                console.log(data)
                this.setState({notes: data})
                if (typeof data !== 'undefined' && data.length > 0) {
                    this.setState({emptyFlag: false})
                }
                else{
                    this.setState({emptyFlag: true})
                }
                this.setState({loading: false})
            })
            .catch((error) => {
            console.error(error);
            this.setState({emptyFlag: true})
        });
    }

    componentWillReceiveProps() {

        let strDate = this.props.date.year + "/" + this.props.date.month + "/" + this.props.date.day
        let dateMonthAsWord = moment(strDate).format('DD-MMM-YYYY');
        console.log(dateMonthAsWord)
        const URL = "http://localhost:8080"
        fetch(URL + "/calendar/entries/" + dateMonthAsWord, {
            headers: {
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then((response) => {
            if(!response.ok) throw new Error(response.status);
            else return response.json();
        })
            .then(data => {
                console.log(data)
                this.setState({notes: data})
                if (typeof data !== 'undefined' && data.length > 0) {
                    this.setState({emptyFlag: false})
                }
                else{
                    this.setState({emptyFlag: true})
                }
                this.setState({loading: false})
            })
            .catch((error) => {
                console.error(error);
                this.setState({emptyFlag: true})
            });
    }
    render(){
        console.log(this.state.emptyFlag)
        return(
            <div>
                {
                    this.state.loading
                            ?<MuiThemeProvider>
                                <div style={{display: 'block', textAlign: 'center'}}>
                                    <CircularProgress />
                                </div>
                            </MuiThemeProvider>
                            :(
                                this.state.emptyFlag
                                    ?this.props.t('calendar.none', { framework: "react-i18next" })
                                    :<NotesListComponent notes={this.state.notes} />
                            )
                }
            </div>
        )
    }
}

export default translate('common')(ExistingNotes);